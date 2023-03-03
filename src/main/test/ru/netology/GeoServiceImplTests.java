package ru.netology;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTests {
    private static GeoService geoService;

    @BeforeAll
    public static void setGeoService() {
        geoService = new GeoServiceImpl();
    }

    @AfterAll
    public static void removeGeoService() {
        geoService = null;
    }

    @DisplayName("Проверить работу метода public Location byIp(String ip)")
    @ParameterizedTest
    @ArgumentsSource(IpProvider.class)
    public void testByIp(String ip, Location expectedLocation) {
        Location actualLocation = geoService.byIp(ip);
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    static class IpProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                    Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                    Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                    Arguments.of("172.0.1.1", new Location("Moscow", Country.RUSSIA, null, 0)),
                    Arguments.of("172.255.2.11", new Location("Moscow", Country.RUSSIA, null, 0)),
                    Arguments.of("96.0.1.0", new Location("New York", Country.USA, null,  0)),
                    Arguments.of("1.1.1.1", null),
                    Arguments.of("0.0.0.0", null)
            );
        }
    }
}
