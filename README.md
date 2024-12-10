# proxyapi

## Swagger UI
Det är möjligt att använda API:et via [Swagger UI](http://localhost:8080/swagger-ui/index.html).

## Anteckningar
* För att säkerställa att det inte finns fler än tre samtidiga anrop till SR:s API från applikationen, har jag implementerat en meddelandekö. På detta sätt köar vi förfrågningar, och eftersom varje förfrågan kommer att göra tre anrop till SR:s API, kommer vi att utföra dessa anrop en efter en synkront, vilket i sin tur uppfyller kravet.
* Jag kunde ha använt RabbitMQ eller ett annat meddelandeköverktyg, men jag hade inte tid att göra detta. Fördelarna med att använda ett sådant verktyg hade varit:
    * Bättre hantering av meddelanden och köer (t.ex hur konfigurering - antal trådar som ska köra samtidigt).
    * Skalbarhet och möjlighet att hantera högre belastning.
    * Möjlighet att distribuera arbetsbelastningen över flera servrar.
    * Inbyggda funktioner för felhantering och återhämtning.