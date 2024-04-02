# Stamps and Coins Service

## How to startup

You can start this SpringBoot application without environment variables.
However, it will preferably use STACO_AWS_LOCALSTACK_IP as a variable to configure where localstack actually is.
You can also add STACO_AWS_LOCALSTACK_PORT to specify the port. It uses 4566 by default

## Test endpoints

1.  [http://localhost:8081/api/staco/service/stacos/all](http://localhost:8081/api/staco/service/stacos/all)

## Example

```kotlin
@Table
data class StaCo(
    @field: Id
    @field: Column("id")
    override val stacoId: UUID?,
    override val description: String?,
    override var year: String?,
    override var value: String?,
    override var currency: CurrencyType,
    override var type: ObjectType,
    override val diameterMM: String? = "",
    override val internalDiameterMM: String? = "",
    override val heightMM: String? = "",
    override val widthMM: String? = "",
    @field: Version
    val version: Long? = null,
) : IStaCo, Persistable<String> {
    override fun getId(): String = stacoId.toString()
    override fun isNew(): Boolean = (version ?: 0) <= 0

}
```

## References

-   [From Reactor to Coroutines](https://blog.frankel.ch/reactor-to-coroutines/)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
