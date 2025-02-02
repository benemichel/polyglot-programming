package com.polyglot.demo.project.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import com.polyglot.demo.project.service.ImportService;
import com.polyglot.demo.project.service.RecommendationService;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;


@Configuration
public class ServiceConfiguration {

    ResourceLoader resourceLoader;

    private String pythonPath = "classpath:";
    private String rubyPath = "classpath:";

    public ServiceConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public RecommendationService getRecommendationService() throws IOException {
        Context context = Context
            .newBuilder("python")
            .allowAllAccess(true)
            .option("python.ForceImportSite", "true")
            .option("python.PythonPath",
                    resourceLoader
                            .getResource(pythonPath)
                            .getFile()
                            .toPath()
                            .toString()
            )
            .option("python.Executable", "venv/bin/graalpy")
            .build();

        Source source = Source.newBuilder("python",
                resourceLoader.getResource(pythonPath + "/RecommendationServiceImpl.py").getFile()
        ).build();

        context.eval(source);

        return context
                .getBindings("python")
                .getMember("RecommendationServiceImpl")
                .as(RecommendationService.class);
    }

    @Bean
    public ImportService getImportService() throws IOException {
        Context context = Context
            .newBuilder("ruby")
            .allowAllAccess(true)
            .build();

        Source source = Source.newBuilder("ruby",
                resourceLoader.getResource(rubyPath + "/ImportServiceImpl.rb").getFile()
        ).build();

        return context
                .eval(source)
                .as(ImportService.class);
    }
}