package ru.netology.i18n;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceImplTests {
    private static LocalizationService localizationService;

    @BeforeAll
    public static void setLocalizationService() {
        localizationService = new LocalizationServiceImpl();
    }

    @AfterAll
    public static void removeLocalizationService() {
        localizationService = null;
    }

    @DisplayName("Проверить работу метода public String locale(Country country)")
    @ParameterizedTest
    @ArgumentsSource(CountriesProvider.class)
    public void testLocal(Country country, String expected) {
        String actual = localizationService.locale(country);
        Assertions.assertEquals(expected, actual);
    }

    static class CountriesProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                    Arguments.of(Country.USA, "Welcome"),
                    Arguments.of(Country.BRAZIL, "Welcome"),
                    Arguments.of(Country.GERMANY, "Welcome")
            );
        }
    }
}
