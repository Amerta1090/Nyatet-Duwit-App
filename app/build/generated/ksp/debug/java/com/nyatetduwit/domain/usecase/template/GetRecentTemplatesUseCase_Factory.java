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
public final class GetRecentTemplatesUseCase_Factory implements Factory<GetRecentTemplatesUseCase> {
  private final Provider<TemplateRepository> repositoryProvider;

  public GetRecentTemplatesUseCase_Factory(Provider<TemplateRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetRecentTemplatesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetRecentTemplatesUseCase_Factory create(
      Provider<TemplateRepository> repositoryProvider) {
    return new GetRecentTemplatesUseCase_Factory(repositoryProvider);
  }

  public static GetRecentTemplatesUseCase newInstance(TemplateRepository repository) {
    return new GetRecentTemplatesUseCase(repository);
  }
}
