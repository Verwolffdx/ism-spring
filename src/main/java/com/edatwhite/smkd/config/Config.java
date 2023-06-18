package com.edatwhite.smkd.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Configuration
public class Config {
    @Bean
    public RestClient getRestClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("ih63zhzypp", "fvxovhx2ei"));

        SSLContextBuilder sslBuilder = SSLContexts.custom()
                .loadTrustMaterial(null, (x509Certificates, s) -> true);
        final SSLContext sslContext = sslBuilder.build();


        RestClient restClient = RestClient.builder(
                        new HttpHost("lstusearch-1500907661.eu-central-1.bonsaisearch.net", 443, "https"))
                .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                                .setDefaultHeaders(
                                        List.of(
                                                new BasicHeader(
                                                        HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())))
                                .addInterceptorLast(
                                        (HttpResponseInterceptor)
                                                (response, context) ->
                                                        response.addHeader("X-Elastic-Product", "Elasticsearch")))
                .build();

        return restClient;
    }

    @Bean
    public  ElasticsearchTransport getElasticsearchTransport() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
    }


    @Bean
    public ElasticsearchClient getElasticsearchClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
        return client;
    }
}
