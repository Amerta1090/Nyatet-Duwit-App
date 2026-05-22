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
public final class SeedDefaultCategoriesUseCase_Factory implements Factory<SeedDefaultCategoriesUseCase> {
  private final Provider<CategoryRepository> repositoryProvider;

  public SeedDefaultCategoriesUseCase_Factory(Provider<CategoryRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SeedDefaultCategoriesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SeedDefaultCategoriesUseCase_Factory create(
      Provider<CategoryRepository> repositoryProvider) {
    return new SeedDefaultCategoriesUseCase_Factory(repositoryProvider);
  }

  public static SeedDefaultCategoriesUseCase newInstance(CategoryRepository repository) {
    return new SeedDefaultCategoriesUseCase(repository);
  }
}
