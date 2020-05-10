package co.electric.snake.strategy.bonostrategy.newdirectionprocessor

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NewDirectionProcessorConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun byFreeEquivalentSafeBestDirections(): ByFreeEquivalentSafeBestDirections {
        return ByFreeEquivalentSafeBestDirections()
    }

    @ConditionalOnMissingBean
    @Bean
    fun byFreeFilteredSafeDirections(): ByFreeFilteredSafeDirections {
        return ByFreeFilteredSafeDirections()
    }

    @ConditionalOnMissingBean
    @Bean
    fun byFarthestBlockingDirections(): ByFarthestBlockingDirections {
        return ByFarthestBlockingDirections()
    }

    @ConditionalOnMissingBean
    @Bean
    fun byEquivalentSafeBestDirections(): ByEquivalentSafeBestDirections {
        return ByEquivalentSafeBestDirections()
    }

    @ConditionalOnMissingBean
    @Bean
    fun byFilteredSafeDirections(): ByFilteredSafeDirections {
        return ByFilteredSafeDirections()
    }

    // @ConditionalOnMissingBean
    // @Bean
    // fun byFreeEquivalentFreeBestDirections(): ByFreeEquivalentFreeBestDirections {
    //     return ByFreeEquivalentFreeBestDirections()
    // }
    //
    // @ConditionalOnMissingBean
    // @Bean
    // fun byFreeFilteredFreeDirections(): ByFreeFilteredFreeDirections {
    //     return ByFreeFilteredFreeDirections()
    // }
    //
    // @ConditionalOnMissingBean
    // @Bean
    // fun byEquivalentFreeBestDirections(): ByEquivalentFreeBestDirections {
    //     return ByEquivalentFreeBestDirections()
    // }
    //
    // @ConditionalOnMissingBean
    // @Bean
    // fun byFilteredFreeDirections(): ByFilteredFreeDirections {
    //     return ByFilteredFreeDirections()
    // }

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
