package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderTests {
    @ParameterizedTest
    @CsvSource({"172.0.0.1, Добро пожаловать",
                "96.0.0.0, Welcome"})
    public void testSendRus(String ip, String expected) {
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Mockito.any())).thenReturn(expected);
        MessageSender sender = new MessageSenderImpl(geoService, localizationService);

        Map<String,String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String actual = sender.send(headers);
        Assertions.assertEquals(expected, actual);
    }
}