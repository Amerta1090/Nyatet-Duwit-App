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
public final class GetTemplatesUseCase_Factory implements Factory<GetTemplatesUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public GetTemplatesUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTemplatesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTemplatesUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new GetTemplatesUseCase_Factory(repositoryProvider);
  }

  public static GetTemplatesUseCase newInstance(TemplateRepository repository) {
    return new GetTemplatesUseCase(repository);
  }
}
