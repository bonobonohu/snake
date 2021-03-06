package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NewDirectionProcessorConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun byFreeEquivalentBestDirections(): ByFreeEquivalentBestDirections {
        return ByFreeEquivalentBestDirections()
    }

    @ConditionalOnMissingBean
    @Bean
    fun byFreeFilteredDirections(): ByFreeFilteredDirections {
        return ByFreeFilteredDirections()
    }

    @ConditionalOnMissingBean
    @Bean
    fun byFarthestBlockingDirections(): ByFarthestBlockingDirections {
        return ByFarthestBlockingDirections()
    }

    @ConditionalOnMissingBean
    @Bean
    fun byEquivalentBestDirections(): ByEquivalentBestDirections {
        return ByEquivalentBestDirections()
    }

    @ConditionalOnMissingBean
    @Bean
    fun byFilteredDirections(): ByFilteredDirections {
        return ByFilteredDirections()
    }

    @ConditionalOnMissingBean
    @Bean
    fun byKispalEsABorz(): ByKispalEsABorz {
        return ByKispalEsABorz()
    }

    @ConditionalOnMissingBean
    @Bean
    fun newDirectionProcessorChain(newDirectionProcessors: MutableSet<NewDirectionProcessor>): NewDirectionProcessorChain {
        return NewDirectionProcessorChain(newDirectionProcessors)
    }

}
