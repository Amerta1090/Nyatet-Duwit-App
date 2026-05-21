package com.nyatetduwit.domain.usecase.template;

import com.nyatetduwit.domain.repository.TemplateRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class IncrementTemplateUsageUseCase_Factory implements Factory<IncrementTemplateUsageUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public IncrementTemplateUsageUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public IncrementTemplateUsageUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static IncrementTemplateUsageUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new IncrementTemplateUsageUseCase_Factory(repositoryProvider);
  }

  public static IncrementTemplateUsageUseCase newInstance(TemplateRepository repository) {
    return new IncrementTemplateUsageUseCase(repository);
  }
}
