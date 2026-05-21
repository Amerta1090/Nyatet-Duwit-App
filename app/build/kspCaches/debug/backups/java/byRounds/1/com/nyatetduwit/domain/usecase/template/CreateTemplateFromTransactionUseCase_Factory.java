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
public final class CreateTemplateFromTransactionUseCase_Factory implements Factory<CreateTemplateFromTransactionUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public CreateTemplateFromTransactionUseCase_Factory(
      Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CreateTemplateFromTransactionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static CreateTemplateFromTransactionUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new CreateTemplateFromTransactionUseCase_Factory(repositoryProvider);
  }

  public static CreateTemplateFromTransactionUseCase newInstance(TemplateRepository repository) {
    return new CreateTemplateFromTransactionUseCase(repository);
  }
}
