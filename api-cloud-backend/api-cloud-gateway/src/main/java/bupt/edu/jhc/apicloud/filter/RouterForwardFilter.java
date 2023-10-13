package bupt.edu.jhc.apicloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description: 路由转发过滤器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/13
 */
@Component
public class RouterForwardFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String realHost = exchange.getRequest().getHeaders().getFirst("realHost");
        if (realHost != null) {
            // 设置请求的目标主机
            exchange.getRequest().mutate().header(HttpHeaders.HOST, realHost).build();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 设置过滤器的执行顺序
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
