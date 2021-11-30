# moneycounterbot

ФУНКЦИОНАЛ
Идея данного бота проста: пользователь вводит определённый бюджет на определённый срок, после чего для него рассчитывается и отслеживается ежедневный бюджет (например, при сумме 30 тыс. руб на 30 дней ежедневный бюджет составит 1000 руб.). 
Изменения бюджета (как в плюс, так и минус) вводятся пользователем вручную посредством бота. Также пользователю дана возможность при желании полностью удалить свои данные.

ИСПОЛЬЗУЕМЫЕ ТЕХНОЛОГИИ
1. Бот написан на Java.
2. В качестве основного фреймворка используется Spring с модулями. Среди прочего используются: Spring Data JPA, Lombok, Spring Security (в настоящий момент не задействован, в будущем будет использоваться шифрования паролей и финансовых данных).
3. При создании бота используется паттерн MVC. В текущем состоянии бота использование паттерна MVC не является критически важным, однако предполагается со временем добавить к приложению веб-интерфейс. В этом случае паттер MVC проявит себя наиболее ярко.
4. Данные пользователей хранятся в таблицах SQL.
5. Функционал Телеграм-бота реализуется посредством сторонней библиотеки.
6. Комментарии в приложении и названия основных функций и переменных написаны на английском языке. Фразы, которые видит пользователь, написаны на русском и вынесены в отдельные классы, так как в будущем планируется добавить мультиязычный интерфейс.
