package com.mayursbapplication.journalApp.services;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider{
    @Override
    public Stream<? extends Arguments> provideArguments (ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().username("Salah").password("Salah").build()),
                        Arguments.of(User.builder().username("Pedro").password("").build())
        );
    }
}

