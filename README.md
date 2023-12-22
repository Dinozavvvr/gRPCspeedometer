# gRPCspeedometer project

Данный проект позволяет локально провести сравнительные испытания двух подходов к межсервисному общению, таких как REST и gRPS, с помощью отображения метрик, настроенных пользователем

## Installation 

Для запуска приложения необходимо собрать docker image worker-node.
```bash
docker build -t worker .
```
При запуске master-node автоматически сеть worker-net для обеспечения взаимодействия внутри сети Docker между worker nodes.
После этого master-node создаст такое количество worker-node контейнеров, сколько указано в параметре depthLevel.
При запуске тестов сначала тестируются запросы между worker-node через REST, а далее через gRPC.

## Usage
Ниже перечислен список основных эндпоинтов master-node

- /grpc-speedometer/tests/start - запускает процесс тестирования, возвращает uuid теста.
- /grpc-speedometer/tests - возвращает последние 10 запущенных тестов.
- /grpc-speedometer/tests/{testCaseId} - возвращает информацию о тесте по testCaseId.
- /grpc-speedometer/tests/{testCaseId}/stop - прерывает работу теста по testCaseId не дожидаясь полного выполнения.
- /grpc-speedometer/tests/{testCaseId}/report - формирует отчет по результатам тестирования теста по testCaseId в виде excel файла. 

## Взаимодействие между master-node и worker-node
### REST
Для тестирования через REST с master-node происходит вызов эндпоинтов worker-node (GET и POST на /worker/rest).
Далее между контейнерами worker-node происходит последовательный вызов этих же самых эндпоинтов уже с измененными данными.
### gRPC
Для тестирования через gRPC с master-node происходит вызов методов worker-node (testGetData и testPostData) через stub канал на master-node.
Дальнейший вызов методов между worker-nodes происходит через stub канал на worker-node c измененными данными.

## Technologies

* java 17
* spring boot 3.2.0
* webflux
* firebase 
* jquery
* apache poi