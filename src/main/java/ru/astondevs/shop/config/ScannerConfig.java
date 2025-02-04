package ru.astondevs.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class ScannerConfig {
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);  // Используем консольный ввод
    }
}
