package com.org.spemajorbackend.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.org.spemajorbackend.service.JwtService;
import com.org.spemajorbackend.utils.JwtUtil;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

@ContextConfiguration(classes = {JwtRequestFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class JwtRequestFilterTest {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void testDoFilterInternal() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        HttpServletResponse response2 = response.getResponse();
        assertTrue(response2 instanceof ResponseFacade);
        assertTrue(request.getInputStream() instanceof DelegatingServletInputStream);
        assertTrue(request.getSession() instanceof MockHttpSession);
        assertTrue(jwtRequestFilter.getEnvironment() instanceof StandardServletEnvironment);
        assertEquals("", request.getContextPath());
        assertEquals("", request.getMethod());
        assertEquals("", request.getRequestURI());
        assertEquals("", request.getServletPath());
        assertEquals("HTTP/1.1", request.getProtocol());
        assertEquals("http", request.getScheme());
        assertEquals("localhost", request.getLocalName());
        assertEquals("localhost", request.getRemoteHost());
        assertEquals("localhost", request.getServerName());
        assertEquals(80, request.getLocalPort());
        assertEquals(80, request.getRemotePort());
        assertEquals(80, request.getServerPort());
        assertEquals(DispatcherType.REQUEST, request.getDispatcherType());
        assertFalse(request.isAsyncStarted());
        assertFalse(request.isAsyncSupported());
        assertFalse(request.isRequestedSessionIdFromUrl());
        assertTrue(request.isActive());
        assertTrue(request.isRequestedSessionIdFromCookie());
        assertTrue(request.isRequestedSessionIdValid());
        assertSame(response.getOutputStream(), response2.getOutputStream());
    }

    @Test
    void testDoFilterInternal2() throws IOException, ServletException, UsernameNotFoundException {
        when(jwtService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "password", new ArrayList<>()));
        when(jwtUtil.validateToken(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(true);
        when(jwtUtil.getUserNameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        DefaultMultipartHttpServletRequest request = mock(DefaultMultipartHttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("42 Main St");
        when(request.getSession(anyBoolean())).thenReturn(new MockHttpSession());
        when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
        verify(jwtService).loadUserByUsername(Mockito.<String>any());
        verify(jwtUtil).getUserNameFromToken(Mockito.<String>any());
        verify(jwtUtil).validateToken(Mockito.<String>any(), Mockito.<UserDetails>any());
        verify(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        verify(request).getRemoteAddr();
        verify(request).getHeader(Mockito.<String>any());
        verify(request).getSession(anyBoolean());
    }

    @Test
    void testDoFilterInternal3() throws IOException, ServletException, UsernameNotFoundException {
        when(jwtService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "password", new ArrayList<>()));
        when(jwtUtil.validateToken(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(false);
        when(jwtUtil.getUserNameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        DefaultMultipartHttpServletRequest request = mock(DefaultMultipartHttpServletRequest.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
        verify(jwtService).loadUserByUsername(Mockito.<String>any());
        verify(jwtUtil).getUserNameFromToken(Mockito.<String>any());
        verify(jwtUtil).validateToken(Mockito.<String>any(), Mockito.<UserDetails>any());
        verify(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        verify(request).getHeader(Mockito.<String>any());
    }
}
