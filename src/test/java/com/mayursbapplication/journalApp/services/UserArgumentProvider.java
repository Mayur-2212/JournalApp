package com.mayursbapplication.journalApp.services;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider{
    @Override
    public Stream<? extends Arguments> provideArguments (ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("Salah").password("Salah").build()),
                        Arguments.of(User.builder().userName("Pedro").password("").build())
        );
    }
}
