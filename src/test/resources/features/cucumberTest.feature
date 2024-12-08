# language: ru

@all
Функция: Добавление товара

  Предыстория:
    Допустим пользователь хочет добавить новый товар
    И пользователь открыл страницу с товарами

  @vegetables
  @only_scenario_hooks
  Структура сценария: Добавление овощей
    Если товара нет в таблице
      | Название   | Тип   | Экзотический   |
      | <Название> | <Тип> | <Экзотический> |
    То пользователь нажимает кнопку "Добавить"
    Когда пользователь заполняет форму для добавления нового товара
      | Название   | Тип   | Экзотический   |
      | <Название> | <Тип> | <Экзотический> |
    И нажимает кнопку "Сохранить"
    То новый товар отображается в списке товаров


    Примеры:
      | Название | Тип | Экзотический |
      | картофель| Овощ| false        |
      | Мандурия | Овощ| true         |

  @fruits
  @only_scenario_hooks
  Структура сценария: Добавление фруктов
    Если товара нет в таблице
      | Название   | Тип   | Экзотический   |
      | <Название> | <Тип> | <Экзотический> |
    То пользователь нажимает кнопку "Добавить"
    Когда пользователь заполняет форму для добавления нового товара
      | Название   | Тип   | Экзотический   |
      | <Название> | <Тип> | <Экзотический> |
    И нажимает кнопку "Сохранить"
    То новый товар отображается в списке товаров

    Примеры:
      | Название | Тип | Экзотический |
      | банан    |Фрукт| false        |
      | Дуриан   |Фрукт| true         |

  @exist_hook
  Структура сценария: Добавление существующего товара
    Если товар есть в таблице
      | Название   | Тип   | Экзотический   |
      | <Название> | <Тип> | <Экзотический> |
    То пользователь нажимает кнопку "Добавить"
    Когда пользователь заполняет форму для добавления нового товара
      | Название   | Тип   | Экзотический   |
      | <Название> | <Тип> | <Экзотический> |
    И нажимает кнопку "Сохранить"
    То не происходит дублирования товаров в списке товаров


    Примеры:
      | Название | Тип | Экзотический |
      | Яблоко   |Фрукт| false        |
