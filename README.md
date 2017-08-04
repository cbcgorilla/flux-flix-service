# flux-flix-service

# 针对Spring WebFlux 的测试用例；
**系统架构：**
```
Spring WebFlux 客户端             Http 客户端
           |                           |
           |                           |
           └--------------------------┘
                          |
                          |
            Spring WebFlux RestController
                          |
                    Service Layer
                          |
                  MongodbRepository
                          |
                      `` Mongodb```