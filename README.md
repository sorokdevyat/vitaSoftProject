    Проектирование и создание системы регистрации и обработки пользовательских заявок.
Задачи : 
* Создать заявку.
* Отправить заявку оператору на рассмотрение
* Просмотреть список заявок с пагинацией и сортировкой
* Принять/отклонить заявку
* Посмотреть список пользователей
* Назначить права оператора.

    Так как в задании сказано, что в бд уже хранятся данные я на всякий случай написал скрипт (2024-05-10-insert_data_in_account_table.xml) -
для инсерта нескольких аккаунтов, **пароль совпадает с юзернеймом и с помощью @PostConstruct (AccountService) создал админа, Username : root, Password : root**.
Но при этом оставил возможность создания аккаунта в контроллере.
Насчет ролевки с несколькими значениями одновременно не со всем понял, поэтому сделал так, что если какая-то роль есть у пользователя, то он может
использовать все методы для этой роли. **Также подключил swagger к проекту, можно перейти по адресу: http://localhost:8080/swagger-ui/index.html.**
