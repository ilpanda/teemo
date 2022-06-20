package com.ilpanda.plugin

import groovy.json.JsonSlurper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState
import org.gradle.api.initialization.Settings

class SubstitutePlugin implements Plugin<Settings> {

    @Override
    void apply(Settings settings) {

        def TAG = "组件联调"

        settings.gradle.settingsEvaluated {

            // 如果需要调试组件，在项目根目录声明一个 module_dependency.json，该文件中配置需要调试组件的本地路径
            // 如果不需要调试组件，可以不用创建。
            def file = new File(settings.rootDir.absolutePath, "include_dependency.json")
            if (!file.exists()) {
                println("$TAG : include_dependency.json 不存在,未开启组件化调试")
                return
            }

            println("$TAG : 解析 include_dependency.json")

            // 解析 json
            def json = file.getText()
            def jsonSlurper = new JsonSlurper()

            // 如果 module_dependency.json 中组件为空，或者没有需要调试(即所有组件的 useModule 为 false )的组件，则返回。
            def config = jsonSlurper.parseText(json)

            // 如果总开关 enable 为 false，直接返回
            if (!config.enable) {
                println("$TAG : 组件化调试总开关 enable 为 false，关闭组件化调试")
                return
            }

            // 如果所有的组件都配置为 false，返回。
            def moduleList = config["module_list"].findAll { it.useModule }
            if (moduleList.isEmpty()) {
                println("$TAG : 没有需要调试的组件")
                return
            }

            // 通过 includeBuild 的方式实现组件化联调
            // 多次 includeBuild 同一路径的项目，默认情况下只有一个配置能生效。
            // 针对上述情况，如果出现多个同一路径的项目，先添加到一个 List 当中。
            Map<String, List> hashMap = new HashMap<>();
            moduleList.findAll {
                it["includeBuild"] == null || it["includeBuild"] == true
            }.each {
                if (!hashMap.containsKey(it["module_dir"])) {
                    hashMap.put(it["module_dir"], new ArrayList())
                }
                def list = hashMap.get(it["module_dir"])
                list.add(it)
            }


            // 支持同时 includeBuild 多个项目
            hashMap.each { projectPath, value ->
                settings.includeBuild(projectPath) {
                    value.each {
                        println("$TAG : includeBuild 方式调试组件，subsititute project ${it.module_name} in ${it.module_dir}")
                    }
                    dependencySubstitution {
                        // substitute：将 aar 依赖替换为本地 project 依赖。
                        value.each { moduleItem ->
                            substitute module(moduleItem.module_group) with project(moduleItem.module_name)
                        }
                    }
                }
            }


            // 通过 include 的方式实现组件化联调
            def includeModuleList = moduleList.findAll {
                it["includeBuild"] == false
            }

            if (includeModuleList.isEmpty()) {
                return
            }

            // 遍历 includeModuleList，将需要调试的组件引入工程。
            includeModuleList.each {
                settings.include ":${it.module_name}"
                settings.project(":${it.module_name}").projectDir = new File(it.module_dir)
                println("$TAG : include 方式调试组件，subsititute project ${it.module_name} in ${it.module_dir}")
            }

            settings.gradle.addProjectEvaluationListener(new ProjectEvaluationListener() {

                @Override
                void beforeEvaluate(Project projectObj) {

                }

                @Override
                void afterEvaluate(Project projectObj, ProjectState state) {
                    projectObj.configurations.all {
                        resolutionStrategy {
                            dependencySubstitution {
                                includeModuleList.each {
                                    substitute module(it.module_group) with project(":${it.module_name}")
                                }
                            }
                        }
                    }
                }
            })

        }
    }


}