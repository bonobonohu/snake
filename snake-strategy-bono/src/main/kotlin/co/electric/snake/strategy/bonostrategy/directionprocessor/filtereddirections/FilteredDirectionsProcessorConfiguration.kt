package co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections

import co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections.AllButMaximumsClosedDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections.ClosedDirectionsProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilteredDirectionsProcessorConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun freeDirectionsProcessor(): FreeDirectionsProcessor {
        return FreeDirectionsProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun closedDirectionsProcessor(): ClosedDirectionsProcessor {
        return AllButMaximumsClosedDirectionsProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun filteredDirectionsProcessor(freeDirectionsProcessor: FreeDirectionsProcessor, closedDirectionsProcessor: ClosedDirectionsProcessor): FilteredDirectionsProcessor {
        return FilteredDirectionsProcessor(freeDirectionsProcessor, closedDirectionsProcessor)
    }

}
