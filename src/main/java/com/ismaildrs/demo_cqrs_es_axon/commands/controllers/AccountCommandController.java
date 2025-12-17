package com.ismaildrs.demo_cqrs_es_axon.commands.controllers;

import com.ismaildrs.demo_cqrs_es_axon.commands.commands.AddAccountCommand;
import com.ismaildrs.demo_cqrs_es_axon.commands.commands.CreditAccountCommand;
import com.ismaildrs.demo_cqrs_es_axon.commands.commands.DebitAccountCommand;
import com.ismaildrs.demo_cqrs_es_axon.commands.commands.UpdateAccountStatusCommand;
import com.ismaildrs.demo_cqrs_es_axon.commands.dto.AddNewAccountRequestDTO;
import com.ismaildrs.demo_cqrs_es_axon.commands.dto.CreditAccountRequestDTO;
import com.ismaildrs.demo_cqrs_es_axon.commands.dto.DebitAccountRequestDTO;
import com.ismaildrs.demo_cqrs_es_axon.commands.dto.UpdateAccountStatusRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;


    @PostMapping
    public CompletableFuture<String> addNewAccount(AddNewAccountRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.intialBalance(),
                request.currency()
        ));
        return response;
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(CreditAccountRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(DebitAccountRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }

    @PutMapping("/changeStatus")
    public CompletableFuture<String> changeAccountStatus(UpdateAccountStatusRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(new UpdateAccountStatusCommand(
                request.accountId(),
                request.accountStatus()
        ));
        return response;
    }

    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex){
        return ex.getMessage();
    }
}
