package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ZuulLoggingFilter extends ZuulFilter {

  /**
   * 반환 값에 따라 필터가 요청의 사전에 동작할지, 사후에 동작할지를 결정
   * @return 필터가 호출되는 순간을 의미하는 문자열
   */
  @Override
  public String filterType() {
    return "pre";
  }

  /**
   * 필터의 호출 순서를 결정
   * @return 호출 순서
   */
  @Override
  public int filterOrder() {
    return 1;
  }

  /**
   * 반드시 필터가 적용되어야하는지 여부를 결정
   * @return true if 반드시 적용되는 경우
   */
  @Override
  public boolean shouldFilter() {
    return true;
  }

  /**
   * 요청의 전이나 후에 실행될 로직 수행
   * @return
   * @throws ZuulException
   */
  @Override
  public Object run() throws ZuulException {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    log.info("************** request uri: {}", request.getRequestURI());
    return null;
  }
}
