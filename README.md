# SOFTENG 306 Project 2
Reverse Engineering and Refactoring

## Team Profile - Wongabe (Team 2)
| Name                | UPI     | Github Username                                           |
| ------------------- | ------- | --------------------------------------------------------- |
| Will Zhang          | yzhb120 | [Will-ZYS](https://github.com/Will-ZYS)                   |
| Lucas Gao           | ygao979 | [lucas2005gao](https://github.com/lucas2005gao)           |
| Martin Tiangco      | mtia116 | [MartinTiangco](https://github.com/MartinTiangco)         |
| Wong Chong          | wcho400 | [OverCry](https://github.com/OverCry)                     |
| Steven Ho           | kho103  | [kcho9906](https://github.com/kcho9906)                   |
| Justin Teo          | jteo158 | [jteo97](https://github.com/jteo97)                       |
| Raymond Chiu        | rchi385 | [raymondhonsumchiu](https://github.com/raymondhonsumchiu) |


## Project Setup in IDE
1. Clone the repository
    ```
    https://github.com/SoftEng306-2020/project-2-team-2.git
    ```
2. Import the project as a Maven project on IntelliJ

## Building the Project
To build an executable jar of the project with maven, run the following on the command line:
```
$ mvn package
```

## Run the project
Run the jar file using java -cp in terminal, running the Main class
```
$ java -cp target/project-2-team-2-1.0.jar com.softeng306.Main
```

## File Structure
```
├─main
│  └─java
│      └─com
│          └─softeng306
│              ├─Database
│              ├─Entity
│              ├─Enum
│              ├─Interfaces
│              │  ├─Database
│              │  ├─Entity
│              │  ├─Managers
│              │  │  └─Validation
│              │  └─Utils
│              ├─Managers
│              │  └─Validation
│              └─Utils
└─test
    ├─java
    │  └─com
    │      └─softeng306
    │          ├─helper
    │          ├─integration
    │          └─unit
    └─resources
        ├─original
        └─sampleOutput
```
