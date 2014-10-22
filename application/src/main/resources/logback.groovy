import ch.qos.logback.classic.Level
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

config = {
    appender('STDOUT', ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = '%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n'
        }
    }
}

productionProfile = {
    root(Level.DEBUG, ['STDOUT'])
}

devProfile = {
    root(Level.DEBUG, ['STDOUT'])
}

config()

def profiles = System.getProperty("spring.profiles.active")?.split(',') ?: [];
profiles.each { curProfile ->
    switch (curProfile) {
        case 'production':
            productionProfile()
            break
        case 'dev':
            devProfile()
            break
        default:
            productionProfile()
    }
}