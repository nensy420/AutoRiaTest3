package com.ria.testsapi;

import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    @Test()
    public void apiTestRestAssured1(){
        given().get("https://developers.ria.com/auto/info?api_key=Q7qNVxdwbZV6xGmpD37IjPjy0AuCRmkYK5lNnRpd&auto_id=22304245").
                then().
                statusCode(200).
                body("autoData.description",equalTo("ЧИСТЫЙ-2013г.в. в ТП.,производство-30;09;2013г.в!!БЕЗ пробега по Украине!!ОТЛИЧНОЕ состояние-НЕ БИТ-100%!!*ОРИГИНАЛЬНЫЙ-МАЛЕНЬКИЙ пробег-1000%-гарантирован(а это-большая РЕДКОСТЬ),в наличии АКТЫ выполненных работ на СТО-06;07;2018г-сделано ПОЛНОЕ обслуживание,авто в ОТЛИЧНОМ техническом состоянии-на руках ЗАКЛЮЧЕНИЕ СЕРВИСНОГО центра!!МОЩНЫЙ и супер ЭКОНОМИЧНЫЙ двигатель 5л./100км!!БОГАТАЯ комплектация-Система динамической стабилизации;Противобуксовочная система;ПОЛНЫЙ-электропакет;Мультируль;Телефон-блютуз;Кондиционер;Бортовой компьютер с монитором;Акустическая система;Парктроники с звуковым сигналом;Электропривод зеркал+Подогрев зеркал;Подогрев стекла;Подогрев сидений;Электро-стеклоподъемники и мн.др. функций-для ВАШЕГО комфорта и безопасности!!ЦЕЛЫЙ-Живой-как технически так и визуально-готов к любым проверкам!!МОЩНАЯ,ДИНАМИЧНАЯ,ЭКОНОМНАЯ,НАДЕЖНАЯ-рабочая лошадка-для БИЗНЕСА и домашнего пользования,мягкая в ходу-ДЕШЕВЫЙ в обслуживании!!В наличии ВСЕ документы для регистрации и постановки на учет!!MIN ТОРГ!"));
    }

    @Test()
    public void apiTestRestAssured2(){
        given().get("https://developers.ria.com/auto/new/models?marka_id=9&category_id=1&api_key=Q7qNVxdwbZV6xGmpD37IjPjy0AuCRmkYK5lNnRpd").
                then().
                statusCode(200).
                body("marka_id[0]",equalTo(9));
    }

    @Test()
    public void apiTestRestAssured3(){
        given().get("https://developers.ria.com/new_to_old?api_key=Q7qNVxdwbZV6xGmpD37IjPjy0AuCRmkYK5lNnRpd&categories.main.id=1&brand.id%5B0%5D=9&year%5B0%5D.gte=2011&year%5B0%5D.lte=2016&custom.not=1&fuel.id%5B5%5D=6&gearbox.id%5B1%5D=2&gearbox.id%5B2%5D=3&size=10%22&countpage=10").
                then().
                statusCode(200).
                body("unrecognized.countpage",equalTo("10"));
    }
}
