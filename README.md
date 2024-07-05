# MinecraftDependencyDownload

Minecraft implementations for [DependencyDownload](https://github.com/Vankka/DependencyDownload)

Heavily inspired by [LuckPerms](https://github.com/lucko/LuckPerms)' dependency management system

## Dependency
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'dev.vankka:minecraftdependencydownload-<artifact>:1.0.0'
}
```

<details>
    <summary>Snapshots</summary>

```groovy
repositories {
    maven {
        url 'https://s01.oss.sonatype.org/content/repositories/snapshots/'
    }
}

dependencies {
    implementation 'dev.vankka:minecraftdependencydownload-<artifact>:1.0.1-SNAPSHOT'
}
```
</details>

## How to use it?

The [Wiki](https://github.com/Vankka/MinecraftDependencyDownload/wiki) has guides for the following platforms:
 - [Bukkit](https://github.com/Vankka/MinecraftDependencyDownload/wiki/Bukkit)
 - [BungeeCord](https://github.com/Vankka/MinecraftDependencyDownload/wiki/Bungee)
 - [Sponge](https://github.com/Vankka/MinecraftDependencyDownload/wiki/Sponge)
 - [Velocity](https://github.com/Vankka/MinecraftDependencyDownload/wiki/Velocity)  

with more to come
