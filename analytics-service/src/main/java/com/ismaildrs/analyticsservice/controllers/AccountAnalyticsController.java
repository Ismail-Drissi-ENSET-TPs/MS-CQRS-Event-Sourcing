package com.ismaildrs.analyticsservice.controllers;

import com.ismaildrs.analyticsservice.entities.AccountAnalytics;
import com.ismaildrs.analyticsservice.queries.GetAccountAnalyticsByAccountIdQuery;
import com.ismaildrs.analyticsservice.queries.GetAllAccountAnalyticsQuery;
import com.ismaildrs.demo_cqrs_es_axon.query.queries.GetAllAccountsQuery;
import io.axoniq.axonserver.grpc.query.SubscriptionQuery;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("/query/account-analytics")
public class AccountAnalyticsController {

    private QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<AccountAnalytics>> getAllAccountAnalytics(){
        return queryGateway.query(new GetAllAccountAnalyticsQuery(), ResponseTypes.multipleInstancesOf(AccountAnalytics.class));
    }

    @GetMapping("/{accountId}")
    public CompletableFuture<AccountAnalytics> getAccountAnalytics(@PathVariable("accountId") String accountId){
        return queryGateway.query(new GetAccountAnalyticsByAccountIdQuery(accountId), ResponseTypes.instanceOf(AccountAnalytics.class));
    }

    @GetMapping(value = "/{accountId}/watch", produces =  MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountAnalytics> watchAccountAnalytics(@PathVariable("accountId") String accountId){
        SubscriptionQueryResult<AccountAnalytics, AccountAnalytics> result = queryGateway.subscriptionQuery(new GetAccountAnalyticsByAccountIdQuery(accountId), AccountAnalytics.class, AccountAnalytics.class);
        return result.initialResult().concatWith(result.updates());
    }

}
