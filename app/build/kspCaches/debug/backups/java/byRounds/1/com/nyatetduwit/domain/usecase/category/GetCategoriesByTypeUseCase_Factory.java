package com.nyatetduwit.domain.usecase.category;

import com.nyatetduwit.domain.repository.CategoryRepository;
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
public final class GetCategoriesByTypeUseCase_Factory implements Factory<GetCategoriesByTypeUseCase> {
  private final Provider<CategoryRepository> repositoryProvider;

  public GetCategoriesByTypeUseCase_Factory(Provider<CategoryRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetCategoriesByTypeUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetCategoriesByTypeUseCase_Factory create(
      Provider<CategoryRepository> repositoryProvider) {
    return new GetCategoriesByTypeUseCase_Factory(repositoryProvider);
  }

  public static GetCategoriesByTypeUseCase newInstance(CategoryRepository repository) {
    return new GetCategoriesByTypeUseCase(repository);
  }
}
