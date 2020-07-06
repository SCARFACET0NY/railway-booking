package com.anton.railway.booking.controller;

import com.anton.railway.booking.dto.TripDto;
import com.anton.railway.booking.service.TripService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {
    private final static String DEPARTURE_CITY = "Kyiv";
    private final static String ARRIVAL_CITY = "Lviv";
    private final static String DATE = "2020-07-09";
    private final static List<TripDto> trips = new ArrayList<>();

    @Mock
    TripService tripService;
    @InjectMocks
    SearchController searchController;
    MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        trips.add(new TripDto());
        trips.add(new TripDto());
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    void getSearchPageTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void searchTest() throws Exception {
        when(tripService.searchTrips(anyString(), anyString())).thenReturn(trips);

        mockMvc.perform(get("/search/")
                .param("departure", DEPARTURE_CITY)
                .param("arrival", ARRIVAL_CITY)
                .param("date", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trips"))
                .andExpect(model().attribute("trips", is(trips)))
                .andExpect(view().name("index"));

        verify(tripService).searchTrips(anyString(), anyString());
    }

    @Test
    void searchWithDateTest() throws Exception {
        when(tripService.searchTrips(anyString(), anyString(), any(LocalDate.class))).thenReturn(trips);

        mockMvc.perform(get("/search/")
                .param("departure", DEPARTURE_CITY)
                .param("arrival", ARRIVAL_CITY)
                .param("date", DATE))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trips"))
                .andExpect(model().attribute("trips", is(trips)))
                .andExpect(view().name("index"));

        verify(tripService).searchTrips(anyString(), anyString(), any(LocalDate.class));
    }
}