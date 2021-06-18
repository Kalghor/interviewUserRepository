# Interview project
RESTowa aplikacja umożliwiająca proste operacje na tabeli użytkowników w bazie H2.


## Opis akcji

- Ładuje dane zawarte w pliku csv do zaladowania do bazy, podana bez rozszerzenia. Plik musi znajdować się w głównym folderze projektu.
localhost:8080/users/load/{nazwa pliku}

- Zwraca listę zawartych w bazie użytkowników w postaci danych JSON.
localhost:8080/users/getUsers


- Zwraca ilość rekordów zawartych w bazie
localhost:8080/users/countUsers

- Zwraca listę zawartych w bazie użytkowników posortowanych po wieku w postaci danych JSON.
localhost:8080/users/byAge

- Usuwa wszystkie rekordy z bazy.
localhost:8080/users/removeAll

- Usuwa rekord o podanym id z bazy.
localhost:8080/users/remove/{id}

- Zwraca najstarszego użytkownika, który posiada numer telefonu w postaci danych JSON.
localhost:8080/users/oldestUserWithPhoneNumber

- Zwraca listę zawartych w bazie użytkowników o podanym nazwisku w postaci danych JSON.
localhost:8080/users/showByLastName/{lastName}
