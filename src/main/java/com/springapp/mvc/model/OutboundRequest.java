package com.springapp.mvc.model;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by vivek on 2/24/14.
 */
public class OutboundRequest {
    private Map<String, String> parameters = new LinkedHashMap<String, String>();
    private Map<String, String> headers = new LinkedHashMap<String, String>();
    private String host;
    private String path;
    public enum Protocol {HTTP, HTTPS}
    private String requestMethod;
    private String requestBody;
    private Protocol protocol = Protocol.HTTP;

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public OutboundRequest(String host, Protocol protocol) {
        this.host = host;
        this.protocol = protocol;
    }

    public OutboundRequest(String host, String path) {
        this.host = host;
        this.path = path;
    }

    public OutboundRequest(Map<String, String> parameters, String host) {

        this.parameters = parameters;
        this.host = host;
    }

    public OutboundRequest() {}

    public OutboundRequest(String host) {
        this.host = host;
    }

    public OutboundRequest(Protocol protocol, String host, Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.parameters = parameters;
    }

    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    public void addParameter(String param, String value) {
        value = value == null ? "" : value;
        parameters.put(param, URLEncoder.encode(value));
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    private String getHostStringFromEnum(Protocol protocol) {
        switch (protocol) {
            case HTTP:
                return "http://";
            case HTTPS:
                return "https://";
            default:
                return "http://";
        }
    }

    public String build() throws MalformedURLException {
        String protocol = getHostStringFromEnum(this.protocol);
        String host = getHost();
        String path = this.path == null ? "" : "/" + getPath() + "";

        if (host == null) {
            throw new MalformedURLException("No host");
        }

        String finalUrl = protocol + host + path;

        if (getParameters().size() > 0)  {
            finalUrl += '?';
            for(Map.Entry<String, String> entry : getParameters().entrySet())  {
                String value = entry.getValue();
                if(value == null) {
                    value = "";
                }
                finalUrl += '&' + entry.getKey() + '=' + value;
            }
        }
        return finalUrl;
    }
}
