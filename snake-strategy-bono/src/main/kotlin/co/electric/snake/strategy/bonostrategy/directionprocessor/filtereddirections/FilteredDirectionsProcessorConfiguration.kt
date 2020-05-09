package co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections

import co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections.ClosedDirectionsCollector
import co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections.ClosedDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.directionprocessor.filtereddirections.closeddirections.FreeCoordinateCountsProcessor
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
    fun freeCoordinateCountsProcessor(): FreeCoordinateCountsProcessor {
        return FreeCoordinateCountsProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun closedDirectionsCollector(): ClosedDirectionsCollector {
        return ClosedDirectionsCollector()
    }

    @ConditionalOnMissingBean
    @Bean
    fun closedDirectionsProcessor(freeCoordinateCountsProcessor: FreeCoordinateCountsProcessor, closedDirectionsCollector: ClosedDirectionsCollector): ClosedDirectionsProcessor {
        return ClosedDirectionsProcessor(freeCoordinateCountsProcessor, closedDirectionsCollector)
    }

    @ConditionalOnMissingBean
    @Bean
    fun filteredDirectionsProcessor(freeDirectionsProcessor: FreeDirectionsProcessor, closedDirectionsProcessor: ClosedDirectionsProcessor): FilteredDirectionsProcessor {
        return FilteredDirectionsProcessor(freeDirectionsProcessor, closedDirectionsProcessor)
    }

}
