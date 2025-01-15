package org.kubesmarts.akrivis.ingestor.scheduler;

import org.junit.jupiter.api.Test;
import org.kubesmarts.akrivis.ingestor.clients.BackstageClientImpl;
import org.kubesmarts.akrivis.ingestor.clients.DefaultClientImpl;
import org.kubesmarts.akrivis.ingestor.clients.GitHubClientImpl;
import org.kubesmarts.akrivis.ingestor.scheduler.IngestorHttpClient;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IngestorHttpClientTest {

    @Test
    public void testExisting() {
        assertInstanceOf(GitHubClientImpl.class, IngestorHttpClient.findHttpClient("GitHub"));
        assertInstanceOf(BackstageClientImpl.class, IngestorHttpClient.findHttpClient("Backstage"));
        assertInstanceOf(DefaultClientImpl.class, IngestorHttpClient.findHttpClient("Default"));
    }

    @Test
    public void testMissing() {
        assertThrows(UnsupportedOperationException.class, () -> IngestorHttpClient.findHttpClient("Missing"));
    }
}