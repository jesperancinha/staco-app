# Stamps and Coins App - Roadmap

## Roadmap to version 0.0.0

2021/11/24
- Implement DynamoDB
- Implement Parameter Store
- S3 on both services

Notes:
1. H2 does not have good support for reactive repos. In both cases, it was very difficult to support CRUD transaction operations successfully. Since the era of the embedded databases is coming to an end, it doesn't also make sense to use an in-memory database like H2 in many cases for exactly the same reasons. This is also the reason why H2 will be removed from all of my remaining repos starting from today.


2021/11/19
- Explore LocalStack

1. Remove H2 dependencies. Replace all usages with test-containers where applicable.
2. Compare PostgreSQL
3. SSO for both back-ends plus option to choose from
4. Clean code and add other authentication profiles
5. Authentication method profiles

## References

- [LocalStack](https://github.com/localstack/localstack)
- [LocalStack Cloud](https://localstack.cloud/)