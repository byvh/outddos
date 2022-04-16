Програма рекомендується для швидкої підготовки готового сценарію мхддос (runner.py)
на основі публікації (повідомлення) в відповідної групі.
Якщо не встановлені будь-які версії Java SE Development Kit та Git, то
завантажте для своєї платформи (ОС) і встановіть їх.

- https://www.oracle.com/java/technologies/downloads/#java17
     (v.17.0.2 updates until at least September 2024)
- https://github.com/git-for-windows/git/releases
     (v.2.35.3)

Копіювання програми (проєкту):
      git clone https://github.com/byvh/outddos.git
      cd outddos

Перед виконанням:
- Скопіювати весь текст публікації з цілями в файл
      inpddos.txt
   що розміщається в папці outddos, або створити.
- Виконати програму:
      java -classpath out\production\outddos project.Main
   (для Windows скорочено:  run.bat)
     Можливо для запуску знадобиться повній шлях до java.exe (для Windows):
     C:\Program Files\Java\jdk-17.0.2\bin\java -classpath ... 
- Результат - 2 файла з ПОТОЧНОЮ ДАТОЮ в імені:
      listTarg_2022.04.16.txt - список ip-цілей (за винятком протоколу udp)
      runDDos_2022.04.16.txt - ГОТОВИЙ командний рядок для запуску мхддос (runner.py).
   (Вставляється в консоль через буфер введення-виведення або може бути скриптом).

   При необхідності можна змінити параметри для runner.py в командному рядку.
   Можливо використання файлу listTarg_2022.04.16.txt для іншого сценарію мхддос (runner.py),
   наприклад для Windows:
      python runner.py -с listTarg_2022.04.16.txt -t 2000 --rpc 2000 --http-methods GET STRESS --table
   або для Linux:
      python3 runner.py ...
- - - - - - - - - - - - - - - - - - - - -
Не з'ясовані питання:
  - Куди дівати протокол udp (начебто він не може використовуватися сумісно с іншими протоколами).
  - Що робити з іменами сайтів (начебто їх дублюють ip-адреси)
  - Не тестувалося для Linux, хоча повинно працювати.
   

