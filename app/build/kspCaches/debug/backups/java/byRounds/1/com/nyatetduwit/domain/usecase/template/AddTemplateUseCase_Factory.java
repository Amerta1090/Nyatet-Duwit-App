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
public final class AddTemplateUseCase_Factory implements Factory<AddTemplateUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public AddTemplateUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddTemplateUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddTemplateUseCase_Factory create(Provider<TemplateRepository> repositoryProvider) {
    return new AddTemplateUseCase_Factory(repositoryProvider);
  }

  public static AddTemplateUseCase newInstance(TemplateRepository repository) {
    return new AddTemplateUseCase(repository);
  }
}
