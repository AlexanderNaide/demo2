package com.example.demo.services;

import com.example.demo.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
//import pro.adeo.commons.graphql.exception.BadRequestException;
//import pro.adeo.supplier.storage.pi.globalFilters.model.Supplier;

import java.util.List;
import java.util.Objects;

@Component(value = "adeo-api-graphql-integration")
public class AdeoApiGraphQLIntegration {

    private HttpGraphQlClient adeoApiGraphQLClient;

    @Autowired
    public void setAdeoApiGraphQLClient(@Qualifier("adeo-api-graphql")HttpGraphQlClient adeoApiGraphQLClient) {
        this.adeoApiGraphQLClient = adeoApiGraphQLClient;
    }

    public Integer checkSupplier(int id){

        String document = "{supplier(id:" + id + "){id}}";

        return Objects.requireNonNull(adeoApiGraphQLClient.document(document)
                .retrieve("supplier")
                .toEntity(Supplier.class)
                .onErrorReturn(new Supplier(-1))
                .block()).id();
    }

    public boolean checkSuppliers(List<Integer> list) {
        for (Integer id : list) {
            int res = checkSupplier(id);
            if (res == -1){
//                throw new BadRequestException("Поставщик отсутствует в базе данных [id: " + id + "]"); // - надо
                throw new RuntimeException("Поставщик отсутствует в базе данных [id: " + id + "]");
            }
        }
        return true;
    }
}
