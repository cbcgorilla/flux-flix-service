# flux-flix-service

###针对Spring WebFlux 的测试用例；
####系统架构：
````
Spring WebFlux 客户端             Http 客户端
           |                           |
           |                           |
           |___________________________|
                          |
                          |
            Spring WebFlux RestController
                          |
                    Service Layer
                          |
                  MongodbRepository
                          |
                       Mongodb

`````

#####支持获取JSON格式数据清单，新增事件， SSE数据定时刷新