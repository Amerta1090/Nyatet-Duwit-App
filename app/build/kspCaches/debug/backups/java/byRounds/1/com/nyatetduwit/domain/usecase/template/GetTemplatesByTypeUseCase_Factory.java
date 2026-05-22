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
public final class GetTemplatesByTypeUseCase_Factory implements Factory<GetTemplatesByTypeUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public GetTemplatesByTypeUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTemplatesByTypeUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTemplatesByTypeUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new GetTemplatesByTypeUseCase_Factory(repositoryProvider);
  }

  public static GetTemplatesByTypeUseCase newInstance(TemplateRepository repository) {
    return new GetTemplatesByTypeUseCase(repository);
  }
}
