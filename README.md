# ДЗ #3: Rest-assured + stubs
___

&nbsp;
### Настройка окружения: 
Для запуска тестов из командной строки необходимо:
1. Установить Java, желательно не ниже 11 версии
2. Установить Maven, если он у вас еще не установлен, это странно, вот инструкция https://maven.apache.org/install.html

&nbsp;
### Запуск тестов:
* `mvn clean test` - запустить все тесты
* `mvn clean test -Dtest=TestClassName` - запуск конкретного тестового класса
* `mvn clean test -Dtest=TestClassName#testName` - запуск конкретного теста

&nbsp;
### Запуск stub-сервера в docker с описанными в json-файле стабами:
Для запуска stub-сервера в docker с заранее сконфигурированными в src/main/resources/mappings/stubs.json стабами
выполнить скрипт start_wiremock.sh
Для добавления новых стабов таким образом необходимо добавить их в src/main/resources/mappings/stubs.json
Запуск тестов с локально поднятым wiremock:
- `mvn clean test -Dwiremock.remote=true`

&nbsp;
### Тест-кейсы:
Для тестирования выбраны эндпоинты POST /pet/{petId} и GET /pet/{petId}

**PetTests** - позитивные кейсы 
- **Предусловие:** Перед каждым тестом создается новый питомец
- **Постусловие:** После каждого теста выполняется удаление созданного питомца
- **checkUpdatePet** - Тест обновления имени и статуса питомца
  - Шаги: POST /pet/{petId} обновляем данные питомца
  - Проверки: 
    - Статус код 200
    - В теле ответа id питомца, новый статус и имя
    - Проверка через эндпоинт GET /pet/{petId}, что имя и статус питомца действительно изменились
- **checkGetPet** - Тест получения данных питомца по id
  - Шаги: GET /pet/{petId} получаем данные питомца
  - Проверки:
    - Статус код 200
    - Валидация по схеме
    - Проверка полного соответствия полученный данных питомца отправленным

**PetNegativeTests** - негативные кейсы
- **checkUpdatePetNotFound** - Тест обновления несуществующего питомца
  - Шаги: POST /pet/{petId} petId - несуществующий id
  - Проверки:
    - Статус код 404
    - В теле ответа message "not found"
- **checkUpdatePetByWrongId** - Тест обновления питомца по некорректному id
  - Шаги: POST /pet/{petId} в качестве petId некорректное значение wrongId
  - Проверки:
    - Статус код 404
    - В теле ответа message "java.lang.NumberFormatException: For input string: \"wrongId\""
- **checkGetPetNotFound** - Тест получения данных несуществующего питомца
  - Шаги: GET /pet/{petId} petId - несуществующий id
  - Проверки:
    - Статус код 404
    - В теле ответа type "error"
    - В теле ответа message "Pet not found"
- **checkGetPetByWrongId** - Тест получения данных питомца по некорректному id
  - Шаги: GET /pet/{petId} в качестве petId некорректное значение wrongId
  - Проверки:
    - Статус код 404
    - В теле ответа message "java.lang.NumberFormatException: For input string: \"wrongId\""
    