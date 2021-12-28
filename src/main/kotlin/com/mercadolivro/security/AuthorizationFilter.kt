package com.mercadolivro.security

import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.service.UserDetailCustomerService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    authenthicationManager: AuthenticationManager,
    private val userDetails: UserDetailCustomerService,
    private val jwtUtil: JwtUtil
) : BasicAuthenticationFilter(authenthicationManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        request.getHeader("Authorization")?.let {
            if (it.startsWith("Bearer")) {
                val authentication = getAuthentication(it.substring(7))
                authentication.let {
                    SecurityContextHolder.getContext().authentication = it
                }
            }
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        if(!jwtUtil.isValidToken(token)) {
            throw AuthenticationException("Token inválido", "999")
        }

        val subject = jwtUtil.getSubject(token)
        userDetails.loadUserByUsername(subject).let {
            return UsernamePasswordAuthenticationToken(it, null, it.authorities);
        }
    }
}