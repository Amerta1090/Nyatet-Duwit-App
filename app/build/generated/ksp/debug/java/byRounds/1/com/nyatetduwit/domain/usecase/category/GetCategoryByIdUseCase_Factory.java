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
public final class GetCategoryByIdUseCase_Factory implements Factory<GetCategoryByIdUseCase> {
  private final Provider<CategoryRepository> repositoryProvider;

  public GetCategoryByIdUseCase_Factory(Provider<CategoryRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetCategoryByIdUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetCategoryByIdUseCase_Factory create(
      Provider<CategoryRepository> repositoryProvider) {
    return new GetCategoryByIdUseCase_Factory(repositoryProvider);
  }

  public static GetCategoryByIdUseCase newInstance(CategoryRepository repository) {
    return new GetCategoryByIdUseCase(repository);
  }
}
