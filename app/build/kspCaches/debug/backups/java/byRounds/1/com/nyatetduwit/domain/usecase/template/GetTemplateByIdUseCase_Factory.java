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
public final class GetTemplateByIdUseCase_Factory implements Factory<GetTemplateByIdUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public GetTemplateByIdUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTemplateByIdUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTemplateByIdUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new GetTemplateByIdUseCase_Factory(repositoryProvider);
  }

  public static GetTemplateByIdUseCase newInstance(TemplateRepository repository) {
    return new GetTemplateByIdUseCase(repository);
  }
}
