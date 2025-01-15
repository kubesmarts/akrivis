package org.kubesmarts.akrivis.ingestor.scheduler;

import org.kubesmarts.akrivis.ingestor.clients.BackstageClientImpl;
import org.kubesmarts.akrivis.ingestor.clients.DefaultClientImpl;
import org.kubesmarts.akrivis.ingestor.clients.GitHubClientImpl;
import org.kubesmarts.akrivis.ingestor.clients.MockGithubClientImpl;

@FunctionalInterface
public interface IngestorHttpClient {

    String fetchData(String url);

    static IngestorHttpClient findHttpClient(String type) {
        return switch (type) {
            case "GitHub" -> new GitHubClientImpl();
            case "Backstage" -> new BackstageClientImpl();
            case "Default" -> new DefaultClientImpl();
            case "GitHubMock" -> new MockGithubClientImpl();
            default -> throw new UnsupportedOperationException("Unknown type: " + type);
        };
    }
}
