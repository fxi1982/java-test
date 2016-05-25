package com.cisco.fxi.freemarker;

import freemarker.cache.TemplateLoader;
import freemarker.template.*;

import java.io.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fxi on 4/28/16.
 */
public class FreeMarkerTest {
    public static void main(String[] args) throws Exception {
        Configuration cfg = buildTemplateConfiguration();
        Template template = cfg.getTemplate("test.json");
        template.process(null, new PrintWriter(System.out));
    }

    static Configuration buildTemplateConfiguration() {
        freemarker.template.Configuration cfg =
                new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_22);
        cfg.setTemplateLoader(new ClassPathTemplateLoader());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocalizedLookup(false);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        cfg.setSharedVariable("__uuid", (TemplateScalarModel) () -> UUID.randomUUID().toString());
        cfg.setSharedVariable("__timestamp", (TemplateScalarModel) () -> String.valueOf(((long)(Math.random() * 10000)) + System.currentTimeMillis()));

/**
        cfg.setSharedVariable("__uuid", new TemplateScalarModel() {
            public String getAsString() throws TemplateModelException {
                return UUID.randomUUID().toString();
            }
        });

        cfg.setSharedVariable("__timestamp", new TemplateScalarModel() {
            public String getAsString() throws TemplateModelException {
                Long value= (long) ((Math.random() * 10000) + System.currentTimeMillis());
                return value.toString();
            }
        });
 **/
        return cfg;
    }



    static class ClassPathTemplateSource {
        private static long ID_SEQUENCE = 1;
        private static synchronized long nextId() {
            return ID_SEQUENCE++;
        }

        private final String path;
        private final long id;

        public ClassPathTemplateSource(String name) {
            if (name == null) throw new IllegalArgumentException("Template name is null.");
            path = name;
            id = nextId();
        }

        public String getPath() {
            return path;
        }

        public long getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClassPathTemplateSource that = (ClassPathTemplateSource) o;

            if (id != that.id) return false;
            return path.equals(that.path);

        }

        @Override
        public int hashCode() {
            int result = path.hashCode();
            result = 31 * result + (int) (id ^ (id >>> 32));
            return result;
        }
    }

    static class ClassPathTemplateLoader implements TemplateLoader {
        private Map<ClassPathTemplateSource, Reader> readerMap = new ConcurrentHashMap<ClassPathTemplateSource, Reader>();

        public Object findTemplateSource(String s) throws IOException {
            return new ClassPathTemplateSource(s);
        }

        public long getLastModified(Object o) {
            return 1000000;
        }

        public Reader getReader(Object o, String s) throws IOException {
            ClassPathTemplateSource resource = (ClassPathTemplateSource)o;
            if (readerMap.containsKey(resource)) {
                closeTemplateSource(readerMap.get(resource));
            }

            Reader reader = new InputStreamReader(FreeMarkerTest.class.getResourceAsStream("/templates/" + resource.getPath()));
            readerMap.put(resource, reader);
            return reader;
        }

        public void closeTemplateSource(Object o) throws IOException {
            ClassPathTemplateSource resource = (ClassPathTemplateSource)o;
            Reader reader = readerMap.get(resource);
            if (reader != null) reader.close();
        }
    }

}
