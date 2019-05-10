package com.bank.component.zuul;

import java.security.Principal;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class ProxyFilter extends ZuulFilter {
	
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Principal auth = ctx.getRequest().getUserPrincipal();
		if(auth != null){
			
			ctx.addZuulRequestHeader("username", auth.getName());
		}
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Principal auth = ctx.getRequest().getUserPrincipal();
		return auth != null;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "pre";
	}
}
