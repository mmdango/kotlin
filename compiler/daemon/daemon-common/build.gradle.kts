
plugins {
    kotlin("jvm")
    id("jps-compatible")
}

dependencies {
    compile(project(":core:descriptors"))
    compile(project(":core:descriptors.jvm"))
    compile(project(":compiler:util"))
    compile(project(":compiler:cli-common"))
    compile(kotlinStdlib())
    compileOnly(project(":js:js.frontend"))
    compileOnly(intellijCoreDep()) { includeJars("intellij-core") }
    compileOnly(intellijDep()) { includeIntellijCoreJarDependencies(project) }
}

sourceSets {
    "main" { projectDefault() }
    "test" {}
}

