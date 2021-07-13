# Parkhaus Team 11
### Praxisprojekt Software Engineering I (SE1)


**Autoren:**
- [*Erik Autenrieth* (eauten2s)](mailto:erik.autenrieth@smail.inf.h-brs.de)
- [*Emre Cetin* (ecetin2s)](mailto:emre.cetin@smail.inf.h-brs.de)
- [*Marian Hönscheid* (mhoens2s)](mailto:marian.hoenscheid@smail.inf.h-brs.de)

SoSe 2021, Hochschule Bonn-Rhein-Sieg (H-BRS)

---

### Inhaltsverzeichnis
___
1. [Beschreibung](#beschreibung )
2. [Projektverzeichnis](#projektverzeichnis)
3. [Benutzung](#benutzung)
4. [Lizenz](#lizenz)
5. [Kontakt](#kontakt)
___


### Projektverzeichnis
___
1. Ein [Digitalisierungskonzept](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/Digitalisierungskonzept) der umgesetzten digitalisierungs Aspekte.
2. Unser  [Kanban-Board](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/boards) zur Übersicht der Tasks.
3. Die wichtigsten [UserStories](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/UserStories) des Projektes.

4. [Priorisierte User Stories](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/Priorisierte-User-Stories).
5. Eine [Story Map](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/Story-Map) der  User Stories.
6. Ein [MVP](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/MVP) (Minimum Viable Product).
7. Ein [UML Use Case-Diagramm](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/UML-Use-Case-Diagramm).
8. Auswahl an UML-Diagrammen:
    - [Robustheitsdiagramm](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/Robustheitsdiagramm)
    - [UML Klassendiagramme](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/UML-Klassendiagramme)
    - [UML Sequenzdiagramme](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/UML-Sequenzdiagramme)
    - [UML Aktivitätsdiagramme](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/UML-Aktivit%C3%A4tsdiagramme)
    - [UML Verteilungsdiagramm](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/UML-Verteilungsdiagramm) (deployment diagram)
    - [UML Zustandsdiagramm](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/UML-Zustandsdiagramm)

9. Java-Interfaces
   - parking_garage
        - [CarIF](src/main/java/com/team11/parking_garage/CarIF.java)

10. JUnit-Tests
    <details><summary>parking_garage</summary>
    
    - [CarTest](src/test/java/com/team11/parking_garage/CarTest.java)
    - [ParkingGarageServletTest](src/test/java/com/team11/parking_garage/ParkingGarageServletTest.java)
    - [StatsTest](src/test/java/com/team11/parking_garage/StatsTest.java)
    - [TicketTest](src/test/java/com/team11/parking_garage/TicketTest.java)
    - [UtilizationTest](src/test/java/com/team11/parking_garage/UtilizationTest.java)
    <details><summary>charts</summary>
      
     - [AveragePriceDurationTest](src/test/java/com/team11/parking_garage/charts/AveragePriceDurationTest.java)
     - [CarTypeTest](src/test/java/com/team11/parking_garage/charts/CarTypeTest.java) 
     - [CustomerTypeTest](src/test/java/com/team11/parking_garage/charts/CustomerTypeTest.java)
     - [SubscriberDurationTest](src/test/java/com/team11/parking_garage/charts/SubscriberDurationTest.java) 
     - [UtilizationChartTest](src/test/java/com/team11/parking_garage/charts/UtilizationChartTest.java)
    </details>
    <details><summary>customers</summary>
             
      - [DiscountedTest](src/test/java/com/team11/parking_garage/customers/DiscountedTest.java)
      - [StandardTest](src/test/java/com/team11/parking_garage/customers/StandardTest.java) 
      - [SubscriberTest](src/test/java/com/team11/parking_garage/customers/SubscriberTest.java)
    </details>

    </details>

11. Java-Klassen
    <details><summary>parking_garage</summary>
    
    - [Car](src/main/java/com/team11/parking_garage/Car.java)
    - [ParkingGarageServlet](src/main/java/com/team11/parking_garage/ParkingGarageServlet.java)
    - [Stats](src/main/java/com/team11/parking_garage/Stats.java)
    - [Ticket](src/main/java/com/team11/parking_garage/Ticket.java)
    - [Utilization](src/main/java/com/team11/parking_garage/Utilization.java)
    <details><summary>charts</summary>
    
    - [AveragePriceDuration](src/main/java/com/team11/parking_garage/charts/AveragePriceDuration.java)
    - [CarType](src/main/java/com/team11/parking_garage/charts/CarType.java)
    - [Chart](src/main/java/com/team11/parking_garage/charts/Chart.java)
    - [CustomerType](src/main/java/com/team11/parking_garage/charts/CustomerType.java)
    - [PieChart](src/main/java/com/team11/parking_garage/charts/PieChart.java)
    - [SubscriberDuration](src/main/java/com/team11/parking_garage/charts/SubscriberDuration.java)
    - [UtilizationChart](src/main/java/com/team11/parking_garage/charts/UtilizationChart.java)
    </details>
    <details><summary>customers</summary>

    - [Customer](src/main/java/com/team11/parking_garage/customers/Customer.java)
    - [Discounted](src/main/java/com/team11/parking_garage/customers/Discounted.java)
    - [Standard](src/main/java/com/team11/parking_garage/customers/Standard.java)
    - [Subscriber](src/main/java/com/team11/parking_garage/customers/Subscriber.java)
    </details>
    <details><summary>management</summary>
    
    - [IncomeStatement](src/main/java/com/team11/parking_garage/management/IncomeStatement.java)
    - [ROICalculator](src/main/java/com/team11/parking_garage/management/ROICalculator.java)
    </details>
     </details>

12. Patternverzeichnis
    - **Singleton Pattern**:
        - [Stats](src/main/java/com/team11/parking_garage/Stats.java)
        - [Utilization](src/main/java/com/team11/parking_garage/Utilization.java)
    - **Template Pattern**:
        - [Chart](src/main/java/com/team11/parking_garage/charts/Chart.java)
            - [AveragePriceDuration](src/main/java/com/team11/parking_garage/charts/AveragePriceDuration.java)
            - [SubscriberDuration](src/main/java/com/team11/parking_garage/charts/SubscriberDuration.java)
            - [UtilizationChart](src/main/java/com/team11/parking_garage/charts/UtilizationChart.java)
        - [PieChart](src/main/java/com/team11/parking_garage/charts/PieChart.java)
            - [CarType](src/main/java/com/team11/parking_garage/charts/CarType.java)
            - [CustomerType](src/main/java/com/team11/parking_garage/charts/CustomerType.java)
13. Eine Beschreibung unserer [Zielkonflikte](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/Zielkonflikte).

14. Ein [Iterationsbericht](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/Iterationsbericht) über fünf Iterrationen.

15. Ein [Summarisches Projektprotokoll](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/Summarisches-Projektprotokoll).

16. Gesamt-[Retrospektive](https://vm-2d21.inf.h-brs.de/mk_se1_ss21_Team_11/mk_se1_ss21_Team_11/-/wikis/Retrospektive) des Projektes.

### Lizenz
[AGPL](LIZENZ)


